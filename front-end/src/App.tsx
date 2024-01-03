import React from 'react';
import './App.css';
import { GameState, Square } from './game';
import BoardSquare from './Square';


interface Props { }

class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
    this.state = { board: [], turn: 1, winner: 0, moveDone: false, workerSelected: 0,
      p1w1initialized: false, p1w2initialized: false, p2w1initialized: false, p2w2initialized: false,
      showDropdown: false, showGods: false, godCardVersion: false, p1GodCard: "", p2GodCard: "", p1selected: false,
      p2selected: false
    }
  }

  newGame = async () => {
    const response = await fetch('/newgame');
    const json = await response.json();
    this.setState(json);
    this.setState({showDropdown: false})
  }

  newGodGame = async (god1: string, god2: string) => {
    const response = await fetch(`/newgodgame?p1=${god1}&p2=${god2}`);
    const json = await response.json();
    this.setState(json);
    this.setState({showDropdown:false, showGods: false})
  }

  pass = async () => {
    const response = await fetch('/pass');
    const json = await response.json();
    this.setState(json);
  }

  play(x: number, y: number): React.MouseEventHandler {
    return async(e) => {
      e.preventDefault();
      let str;
      if (!this.state.p1w1initialized)
        str =`/p1w1?x=${x}&y=${y}`
      else if (!this.state.p1w2initialized)
        str = `/p1w2?x=${x}&y=${y}`
      else if (!this.state.p2w1initialized)
        str = `/p2w1?x=${x}&y=${y}`
      else if (!this.state.p2w2initialized)
        str = `/p2w2?x=${x}&y=${y}`
      else if (!this.state.moveDone) {
        if (this.state.workerSelected !== 0)
          str = `/move?x=${x}&y=${y}&p=${this.state.turn}&w=${this.state.workerSelected}`
        else
          str = `/selectW?x=${x}&y=${y}`
      } else
        str = `/build?x=${x}&y=${y}&p=${this.state.turn}&w=${this.state.workerSelected}`

      const response = await fetch(str) //set worker selected to null after every successful move&build
      const json = await response.json();

      this.setState(json);
    }
  }

  createSquare(square: Square, index: number): React.ReactNode {
    if (!square.isComplete)
        /**
         * key is used for React when given a list of items. It
         * helps React to keep track of the list items and decide
         * which list item need to be updated.
         * @see https://reactjs.org/docs/lists-and-keys.html#keys
         */
      return (
          <div key={index}>
            <a href='/' onClick={this.play(square.x, square.y)}>
              <BoardSquare square={square}></BoardSquare>
            </a>
          </div>
      )
    else
      return (
          <div key={index}><BoardSquare square={square}></BoardSquare></div>
      )
  }

  setGod(god: string): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault()
      if (!this.state.p1selected)
        this.setState({p1GodCard: god, p1selected: true})
      else if (!this.state.p2selected) {
        this.setState({p2selected: true})
        this.newGodGame(this.state.p1GodCard, god)
      }
    }
  }

  showGodCards() {
    return (
        <div>
          <div key="Demeter"><button onClick={this.setGod("Demeter")}>Demeter</button></div>
          <div key="Minotaur"><button onClick={this.setGod("Minotaur")}>Minotaur</button></div>
          <div key="Pan"><button onClick={this.setGod("Pan")}>Pan</button></div>
          <div key="No God Card"><button onClick={this.setGod("No God Card")}>No God Card</button></div>
        </div>
    )
  }

  createDropdown() {
    return (
        <div>
          <button className="gameType" onClick={this.newGame}>Classic</button>
          <button className="gameType" onClick={this.toggleDropdownGod}>God Cards</button>
        </div>
      )
    }


  createInstruction(): React.ReactNode {
    if (this.state.godCardVersion) {
      if (!this.state.p1selected)
        return 'Player1 select God card'
      else if (!this.state.p2selected)
        return 'Player2 select God card'
    }

    if (this.state.winner !== 0)
      return `Player${this.state.winner} wins!`
    else if (!this.state.p1w1initialized)
      return `Player1 initialize 1st worker`
    else if (!this.state.p1w2initialized)
      return 'Player1 initialize 2nd worker'
    if (!this.state.p2w1initialized)
      return `Player2 initialize 1st worker`
    else if (!this.state.p2w2initialized)
      return 'Player2 initialize 2nd worker'
    else if (!this.state.moveDone) {
      if (this.state.workerSelected === 0)
        return `Player${this.state.turn} select a worker to move`
      else
        return `Player${this.state.turn} select a square to move to`
    } else
      return `Player${this.state.turn} select a square to build`
  }

  toggleDropdown = () => {
    this.setState({showDropdown: !this.state.showDropdown})
  }

  toggleDropdownGod = () => {
    this.setState({showGods: !this.state.showGods, godCardVersion: true, p1selected:false, p2selected: false})
  }


  componentDidMount(): void {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
  }

  render(): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    return (
        <div>
          <div id="instructions">{this.createInstruction()}</div>
          <div id="board">
            {this.state.board.map((square, i) => this.createSquare(square, i))}
          </div>
          <div id="bottombar">
            <button className="dropbtn" onClick={this.toggleDropdown}>New Game</button>
            <div id="dropdown-content" className={this.state.showDropdown ? "show" : "hide"}>
              {this.createDropdown()}
            </div>
            <div id="godcards-content" className={this.state.showGods ? "show" : "hide"}>
              {this.showGodCards()}
            </div>
            <button className="passbtn" onClick={this.pass}>Pass</button>
          </div>
        </div>
    );
  }
}

export default App;