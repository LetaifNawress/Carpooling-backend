"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.mapParticipationToDTO = void 0;
// src/models/Participation.ts
const mongoose_1 = __importDefault(require("mongoose"));
const participationSchema = new mongoose_1.default.Schema({
    clientID: { type: Number, required: true },
    carpoolingID: { type: Number, required: true },
    etat: { type: Number, default: 1 },
});
const Participation = mongoose_1.default.model('Participation', participationSchema);
const mapParticipationToDTO = (participation) => {
    return {
        participationID: participation._id,
        clientID: participation.clientID,
        carpoolingID: participation.carpoolingID,
        etat: participation.etat || 1,
    };
};
exports.mapParticipationToDTO = mapParticipationToDTO;
exports.default = Participation;
