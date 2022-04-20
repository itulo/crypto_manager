import React, { Component } from 'react';
import { Table } from "reactstrap";
// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// To make rows collapsible
import "bootstrap/js/src/collapse.js";

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
    //const total = balances.map(b => b.amount*b.pricePerUnit).reduce(total, currentValue) => total = total + currentValue.prix,0);
    const total = balances.reduce((total, b) => total = total + (b.amount*b.pricePerUnit), 0);

    return (
        <>
          <h2>{name}</h2>
          <Table responsive striped bordered hover size="sm">
            <thead>
              <tr>
                <th>Date</th>
                <th>Coin</th>
                <th>Amount</th>
                <th>Price Per Unit</th>
                <th>Total (EUR)</th>
              </tr>
            </thead>
            <tbody>
              {balances.map(b =>
              <tr data-toggle="collapse" data-target=".multi-collapse1" aria-controls="multiCollapseExample1">
                <td>{b.date}</td>
                <td>{b.coin}</td>
                <td>{b.amount}</td>
                <td>{b.pricePerUnit}</td>
                <td>{Number(b.amount*b.pricePerUnit).toFixed(2)}</td>
              </tr>
              )}
              <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><b>Total</b></td>
                <td><b>{Number(total).toFixed(2)}</b></td>
              </tr>
            </tbody>
          </Table>
        </>
    );
  }
}
export default ExchangeSummary;