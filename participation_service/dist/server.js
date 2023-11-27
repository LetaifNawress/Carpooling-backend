"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const cors_1 = __importDefault(require("cors"));
class Server {
    constructor(port) {
        this.port = port;
    }
    start() {
        const app = (0, express_1.default)();
        // Enable CORS for all routes
        app.use((0, cors_1.default)({ origin: 'http://localhost:4200' }));
        app.get('/', (req, res) => {
            res.send('TypeScript avec Express!');
        });
        app.listen(this.port, () => {
            console.log('Server started');
        });
    }
}
exports.default = Server;
