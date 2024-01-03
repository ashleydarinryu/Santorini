interface GameState {
    board: Square[];
    turn: number;
    winner: number; // winner maybe null
    moveDone: boolean;
    workerSelected: number;
    p1w1initialized: boolean;
    p1w2initialized: boolean;
    p2w1initialized: boolean;
    p2w2initialized: boolean;
    showDropdown: boolean;
    showGods: boolean;
    godCardVersion: boolean;
    p1GodCard: string;
    p2GodCard: string;
    p1selected: boolean;
    p2selected: boolean;
  }
  
  interface Square {
    text: string;
    x: number;
    y: number;
    occupied: boolean;
    isComplete: boolean;
  }
  
  interface GodCard {
    name: string;
    taken: boolean;
  }
  
  export type { GameState, Square, GodCard }