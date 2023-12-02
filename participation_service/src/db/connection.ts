// src/db/connection.ts
import mongoose from 'mongoose';

export function connectToDatabase() {
  mongoose.connect('mongodb://127.0.0.1:27017/participations', {
    // Les autres options de connexion ici...
  });

  const db = mongoose.connection;
  db.on('error', console.error.bind(console, 'Erreur de connexion à la base de données :'));
  db.once('open', () => {
    console.log('Connecté à la base de données MongoDB');
  });

  return db;
}