"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.connectToDatabase = void 0;
// src/db/connection.ts
const mongoose_1 = __importDefault(require("mongoose"));
function connectToDatabase() {
    mongoose_1.default.connect('mongodb://127.0.0.1:27017/participations', {
    // Les autres options de connexion ici...
    });
    const db = mongoose_1.default.connection;
    db.on('error', console.error.bind(console, 'Erreur de connexion à la base de données :'));
    db.once('open', () => {
        console.log('Connecté à la base de données MongoDB');
    });
    return db;
}
exports.connectToDatabase = connectToDatabase;
