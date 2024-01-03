import React from 'react';
import { Square } from './game';

interface Props {
  square: Square
}

class BoardSquare extends React.Component<Props> {
  render(): React.ReactNode {
    const isComplete = this.props.square.isComplete ? 'isComplete' : '';
    return (
        <div className={`square ${isComplete}`}><div className={'text'}>{this.props.square.text}</div></div>
    )
  }
}

export default BoardSquare;