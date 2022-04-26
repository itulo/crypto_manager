import React, { Component } from 'react';
import { Table } from "reactstrap";
// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";

class Summary extends Component {

  sumAll(balances){
    return balances.flat(1).reduce((total, b) => total = total + (b.amount*b.pricePerUnit), 0);
  }

  render() {
    const balancesByExchange = this.props.balancesByExchange;
    const total = this.sumAll(Object.values(balancesByExchange));

    return (
        <>
          <Table responsive striped bordered className={"w-25"}>
            <thead>
              <tr>
                <th>Exchange</th>
                <th>Total</th>
              </tr>
            </thead>
            <tbody>
                {Object.entries(balancesByExchange).map( ([key, value]) =>
                  <tr key={key}>
                    <td>{key}</td>
                    <td>{Number(this.sumAll(value)).toFixed(2)}</td>
                  </tr>
                )}
              <tr>
                <td><b>Total </b></td>
                <td><b>{Number(total).toFixed(2)}</b></td>
              </tr>
            </tbody>
          </Table>
        </>
    );
  }
}
export default Summary;