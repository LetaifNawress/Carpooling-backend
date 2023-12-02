"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const mongoose_1 = __importDefault(require("mongoose"));
const participationRoutes_1 = __importDefault(require("./routes/participationRoutes"));
const fs_1 = __importDefault(require("fs"));
const cors_1 = __importDefault(require("cors")); // Import the cors middleware
function connectToEureka() {
    fs_1.default.readFile('eureka-config.json', 'utf8', (err, data) => {
        if (err) {
            console.error('Error reading the configuration file:', err);
            return;
        }
        try {
            const config = JSON.parse(data);
            const Eureka = require('eureka-js-client').Eureka;
            const eureka = new Eureka(config.eureka);
            eureka.start((error) => {
                if (error) {
                    console.log('Error registering with Eureka: ' + error);
                }
                else {
                    console.log('Microservice registered with Eureka');
                }
            });
        }
        catch (parseError) {
            console.error('Error parsing JSON file:', parseError);
            process.exit(1);
        }
    });
}
const app = (0, express_1.default)();
const PORT = process.env.PORT || 3002;
// Enable CORS for all routes
app.use((0, cors_1.default)());
app.use(body_parser_1.default.json());
mongoose_1.default
    .connect('mongodb://127.0.0.1:27017/participations')
    .then(() => {
    console.log('Connected to the MongoDB database');
})
    .catch((err) => {
    console.error('Database connection error:', err);
    process.exit(1);
});
connectToEureka();
app.use('/', participationRoutes_1.default);
app.listen(PORT, () => {
    console.log(`Server is listening on port ${PORT}`);
});
