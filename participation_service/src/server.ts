import express, { Request, Response } from 'express';
import cors from 'cors';

export default class Server {
  constructor(private port: number) {}

  public start(): void {
    const app = express();

    // Enable CORS for all routes
    app.use(cors({ origin: 'http://localhost:4200' }));

    app.get('/', (req: Request, res: Response) => {
      res.send('TypeScript avec Express!');
    });

    app.listen(this.port, () => {
      console.log('Server started');
    });
  }
}
