"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.deleteParticipation = exports.updateParticipation = exports.getParticipationByIdAndEtat = exports.countParticipationsAnnule = exports.countParticipationsByCarpoolingIDAndEtat = exports.getParticipationById = exports.createParticipation = exports.getParticipations = void 0;
const Participation_1 = __importStar(require("../models/Participation"));
// GET - Retrieve all participations
const getParticipations = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const participations = yield Participation_1.default.find().lean().exec();
        const participationDTOs = participations.map((participation) => (0, Participation_1.mapParticipationToDTO)(participation));
        return res.json(participationDTOs);
    }
    catch (err) {
        return res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.getParticipations = getParticipations;
// POST - Create a new participation
const createParticipation = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const { clientID, carpoolingID, etat } = req.body;
    if (!clientID || !carpoolingID || !etat) {
        return res.status(400).json({ message: 'clientID, carpoolingID, and etat are required' });
    }
    const newParticipation = new Participation_1.default({ clientID, carpoolingID, etat });
    try {
        const savedParticipation = yield newParticipation.save();
        const participationDTO = (0, Participation_1.mapParticipationToDTO)(savedParticipation);
        res.status(201).json(participationDTO);
    }
    catch (err) {
        res.status(400).json({ message: err instanceof Error ? err.message : 'Bad Request' });
    }
});
exports.createParticipation = createParticipation;
// GET - Retrieve a participation by ID
const getParticipationById = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const carpoolingID = req.params.id;
    try {
        console.log('Attempting to find participation with ID:', carpoolingID);
        // Use findOne for non-ObjectId identifiers
        const participation = yield Participation_1.default.findOne({ carpoolingID }).lean().exec();
        if (!participation) {
            console.log('Participation not found');
            return res.status(404).json({ message: 'Participation not found' });
        }
        console.log('Participation found:', participation);
        const participationDTO = (0, Participation_1.mapParticipationToDTO)(participation);
        res.status(200).json(participationDTO);
    }
    catch (err) {
        console.error('Error:', err);
        res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.getParticipationById = getParticipationById;
// GET - Count participations by carpoolingID and etat
const countParticipationsByCarpoolingIDAndEtat = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const etat = 2; // Assuming etat=2, modify as needed
    try {
        console.log('Counting participations with carpoolingID and etat:', etat);
        // Use countDocuments with the find query
        const count = yield Participation_1.default.countDocuments({ etat });
        console.log(count);
        res.status(200).json(count); // Send only the count, not inside an object
    }
    catch (err) {
        console.error('Error:', err);
        res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.countParticipationsByCarpoolingIDAndEtat = countParticipationsByCarpoolingIDAndEtat;
const countParticipationsAnnule = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const etat = 1; // Assuming etat=2, modify as needed
    try {
        console.log('Counting participations annule:', etat);
        // Use countDocuments with the find query
        const count = yield Participation_1.default.countDocuments({ etat });
        console.log(count);
        res.status(200).json(count); // Send only the count, not inside an object
    }
    catch (err) {
        console.error('Error:', err);
        res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.countParticipationsAnnule = countParticipationsAnnule;
// GET - Retrieve a participation by ID and etat
const getParticipationByIdAndEtat = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const etat = 2; // Assuming etat=2, modify as needed
    try {
        console.log('Attempting to find participations with ID and etat:', etat);
        // Use find with multiple conditions
        const participations = yield Participation_1.default.find({ etat }).lean().exec();
        if (!participations || participations.length === 0) {
            console.log('Participations not found');
            return res.status(404).json({ message: 'Participations not found' });
        }
        console.log('Participations found:', participations);
        const participationDTOs = participations.map((participation) => (0, Participation_1.mapParticipationToDTO)(participation));
        res.status(200).json(participationDTOs);
    }
    catch (err) {
        console.error('Error:', err);
        res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.getParticipationByIdAndEtat = getParticipationByIdAndEtat;
// PUT - Update a participation by its ID
const updateParticipation = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const participationId = req.params.id;
    const { clientID, carpoolingID, etat } = req.body;
    if (!clientID || !carpoolingID || !etat) {
        return res.status(400).json({ message: 'clientID, carpoolingID, and etat are required' });
    }
    try {
        const updatedParticipation = yield Participation_1.default.findByIdAndUpdate(participationId, { clientID, carpoolingID, etat }, { new: true }).lean().exec();
        if (!updatedParticipation) {
            return res.status(404).json({ message: 'Participation not found' });
        }
        const participationDTO = (0, Participation_1.mapParticipationToDTO)(updatedParticipation);
        res.status(200).json(participationDTO);
    }
    catch (err) {
        res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.updateParticipation = updateParticipation;
// DELETE - Delete a participation by its ID
const deleteParticipation = (req, res) => __awaiter(void 0, void 0, void 0, function* () {
    const participationId = req.params.id;
    try {
        const deletedParticipation = yield Participation_1.default.findByIdAndRemove(participationId).lean().exec();
        if (!deletedParticipation) {
            return res.status(404).json({ message: 'Participation not found' });
        }
        const participationDTO = (0, Participation_1.mapParticipationToDTO)(deletedParticipation);
        res.status(204).json(participationDTO);
    }
    catch (err) {
        res.status(500).json({ message: err instanceof Error ? err.message : 'Internal Server Error' });
    }
});
exports.deleteParticipation = deleteParticipation;
