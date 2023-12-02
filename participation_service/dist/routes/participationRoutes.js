"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// participationRoutes.ts
const express_1 = __importDefault(require("express"));
const participationController_1 = require("../controllers/participationController");
const router = express_1.default.Router();
router.get('/participation', participationController_1.getParticipations);
router.post('/participation', participationController_1.createParticipation);
router.delete('/participation/:id', participationController_1.deleteParticipation);
router.get('/participation/:id', participationController_1.getParticipationById);
router.get('/participationCount', participationController_1.countParticipationsByCarpoolingIDAndEtat);
router.get('/participationAnnuledCount', participationController_1.countParticipationsAnnule);
router.get('/participationEtat', participationController_1.getParticipationByIdAndEtat);
exports.default = router;
