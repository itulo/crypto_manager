import React, { Component } from 'react';

class CoinBalanceRow extends Component {
  render(){
    const balance = this.props.balance;

    return (
      <tr key={balance.id}>
        <td>{balance.date}</td>
        <td>{balance.coin}</td>
        <td>{balance.amount}</td>
        <td>{balance.pricePerUnit}</td>
        <td>{Number(balance.amount*balance.pricePerUnit).toFixed(2)}</td>
       </tr>
    );
  }
}
export default CoinBalanceRow;