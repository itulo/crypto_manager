import React, { Component } from 'react';
import { Button, Collapse, Table } from "reactstrap";
import CoinBalanceRow from './CoinBalanceRow'
// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";

class ExchangeSummary extends Component {
  constructor(props){
    super(props);
    this.state = {
      name: this.props.name,
      balances: this.props.balances,
      collapsed: false
    }

    // This binding is necessary to make `this` work in the callback
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      collapsed: !prevState.collapsed
    }));
  }

  render() {
    const name = this.state.name.replace("_", " ");
    const balances = this.state.balances;
    const collapsed = this.state.collapsed;
    const total = balances.reduce((total, b) => total = total + (b.amount*b.pricePerUnit), 0);
    const collapseIcon = collapsed ? "bi bi-arrows-angle-expand" : "bi bi-arrows-angle-contract"

    return (
      //TODO when user removes a balance row and components re-render, collapsed exchanges are not collapsed anymore, store as state
        <>
          <div>
            <Button color="info" onClick={this.toggle} className={collapseIcon+' mb-2'}> {name}</Button>
          </div>
          <Collapse isOpen={!this.state.collapsed}>
              <Table responsive striped bordered className={"w-50"}>
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Coin</th>
                    <th>Amount</th>
                    <th>Price Per Unit</th>
                    <th>Total (EUR)</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {balances.map(b =>
                    <CoinBalanceRow
                      key={b.id}
                      balance={b}
                      onDeleteBalance={this.props.onDeleteBalance}/>
                  )}
                  <tr>
                    <td colSpan={4} className="text-end"><b>Total </b></td>
                    <td><b>{Number(total).toFixed(2)}</b></td>
                  </tr>
                </tbody>
              </Table>
          </Collapse>
        </>
    );
  }
}
export default ExchangeSummary;