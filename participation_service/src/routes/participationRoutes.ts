// participationRoutes.ts
import express from 'express';
import {
  getParticipations,
  createParticipation,
  deleteParticipation,
  getParticipationById,
  getParticipationByIdAndEtat,
  countParticipationsByCarpoolingIDAndEtat,
  countParticipationsAnnule,
  getParticipantsByCarpoolingId,
} from '../controllers/participationController';

const router = express.Router();

router.get('/participation', getParticipations);
router.post('/participation', createParticipation);
router.delete('/participation/:id', deleteParticipation);
router.get('/participation/:id', getParticipationById);
router.get('/participationCount', countParticipationsByCarpoolingIDAndEtat);
router.get('/participationAnnuledCount', countParticipationsAnnule);

router.get('/participationEtat', getParticipationByIdAndEtat);

router.get('/p/participants/cov/:id', getParticipantsByCarpoolingId); // Updated route definition

export default router;