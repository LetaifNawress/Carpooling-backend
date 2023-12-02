// src/models/Participation.ts
import mongoose from 'mongoose';

export interface ParticipationDTO {
  participationID: number; 
  clientID: number;
  carpoolingID: number;
  etat: number;
}

const participationSchema = new mongoose.Schema({
  clientID: { type: Number, required: true },
  carpoolingID: { type: Number, required: true },
  etat: { type: Number, default: 1 },
});

const Participation = mongoose.model('Participation', participationSchema);

export const mapParticipationToDTO = (participation: any): ParticipationDTO => {
  return {
    participationID: participation._id, 
    clientID: participation.clientID,
    carpoolingID: participation.carpoolingID,
    etat: participation.etat || 1,
  };
};

export default Participation;
