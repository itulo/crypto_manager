import React, { Component } from 'react';

class ExchangeSummary extends Component {
  constructor(props){
    super(props);
    this.state = {
      name: this.props.name,
      balances: this.props.balances
    }
  }

  render() {
    const name = this.state.name;
    const balances = this.state.balances;

    return (
      <div>
       <h2>{name}</h2>
        {balances.map(b =>
          <div key={b.id}>
            {b.date} {b.exchange} {b.coin} {b.amount} {b.pricePerUnit} {b.fiat}
          </div>
        )}
      </div>
    );
  }
}
export default ExchangeSummary;