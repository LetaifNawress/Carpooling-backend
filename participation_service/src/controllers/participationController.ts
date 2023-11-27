import { Request, Response } from 'express';
import Participation, { ParticipationDTO, mapParticipationToDTO } from '../models/Participation';

// GET - Retrieve all participations
export const getParticipations = async (req: Request, res: Response) => {
  try {
    const participations = await Participation.find().lean().exec();
    const participationDTOs: ParticipationDTO[] = participations.map((participation: any) =>
      mapParticipationToDTO(participation)
    );
    return res.json(participationDTOs);
  } catch (err) {
    return res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};

// POST - Create a new participation
export const createParticipation = async (req: Request, res: Response) => {
  const { clientID, carpoolingID, etat } = req.body as ParticipationDTO;

  if (!clientID || !carpoolingID || !etat) {
    return res.status(400).json({ message: 'clientID, carpoolingID, and etat are required' });
  }

  const newParticipation = new Participation({ clientID, carpoolingID, etat });

  try {
    const savedParticipation = await newParticipation.save();
    const participationDTO = mapParticipationToDTO(savedParticipation);
    res.status(201).json(participationDTO);
  } catch (err) {
    res.status(400).json({ message: err instanceof Error ? err.message : 'Bad Request' });
  }
};

// GET - Retrieve a participation by ID
export const getParticipationById = async (req: Request, res: Response) => {
  const carpoolingID = req.params.id;

  try {
    console.log('Attempting to find participation with ID:', carpoolingID);

    // Use findOne for non-ObjectId identifiers
    const participation = await Participation.findOne({ carpoolingID }).lean().exec();

    if (!participation) {
      console.log('Participation not found');
      return res.status(404).json({ message: 'Participation not found' });
    }

    console.log('Participation found:', participation);

    const participationDTO = mapParticipationToDTO(participation);
    res.status(200).json(participationDTO);
  } catch (err) {
    console.error('Error:', err);
    res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};

// GET - Count participations by carpoolingID and etat
export const countParticipationsByCarpoolingIDAndEtat = async (req: Request, res: Response) => {
  const etat = 2; // Assuming etat=2, modify as needed

  try {
    console.log('Counting participations with carpoolingID and etat:', etat);

    // Use countDocuments with the find query
    const count = await Participation.countDocuments({  etat });

    console.log(count);

    res.status(200).json(count); // Send only the count, not inside an object
  } catch (err) {
    console.error('Error:', err);
    res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};


export const countParticipationsAnnule = async (req: Request, res: Response) => {
  const etat = 1; // Assuming etat=2, modify as needed

  try {
    console.log('Counting participations annule:', etat);

    // Use countDocuments with the find query
    const count = await Participation.countDocuments({  etat });

    console.log(count);

    res.status(200).json(count); // Send only the count, not inside an object
  } catch (err) {
    console.error('Error:', err);
    res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};

// GET - Retrieve a participation by ID and etat
export const getParticipationByIdAndEtat = async (req: Request, res: Response) => {
 
  const etat = 2; // Assuming etat=2, modify as needed

  try {
    console.log('Attempting to find participations with ID and etat:', etat);

    // Use find with multiple conditions
    const participations = await Participation.find({  etat }).lean().exec();

    if (!participations || participations.length === 0) {
      console.log('Participations not found');
      return res.status(404).json({ message: 'Participations not found' });
    }

    console.log('Participations found:', participations);

    const participationDTOs = participations.map((participation: any) => mapParticipationToDTO(participation));
    res.status(200).json(participationDTOs);
  } catch (err) {
    console.error('Error:', err);
    res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};


// PUT - Update a participation by its ID
export const updateParticipation = async (req: Request, res: Response) => {
  const participationId = req.params.id;
  const { clientID, carpoolingID, etat } = req.body as ParticipationDTO;

  if (!clientID || !carpoolingID || !etat) {
    return res.status(400).json({ message: 'clientID, carpoolingID, and etat are required' });
  }

  try {
    const updatedParticipation = await Participation.findByIdAndUpdate(
      participationId,
      { clientID, carpoolingID, etat },
      { new: true }
    ).lean().exec();

    if (!updatedParticipation) {
      return res.status(404).json({ message: 'Participation not found' });
    }

    const participationDTO = mapParticipationToDTO(updatedParticipation);
    res.status(200).json(participationDTO);
  } catch (err) {
    res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};

// DELETE - Delete a participation by its ID
export const deleteParticipation = async (req: Request, res: Response) => {
  const participationId = req.params.id;

  try {
    const deletedParticipation = await Participation.findByIdAndRemove(participationId).lean().exec();
    if (!deletedParticipation) {
      return res.status(404).json({ message: 'Participation not found' });
    }
    const participationDTO = mapParticipationToDTO(deletedParticipation);
    res.status(204).json(participationDTO);
  } catch (err) {
    res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
  }
};
