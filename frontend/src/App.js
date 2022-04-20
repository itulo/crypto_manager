import React, { Component } from 'react';
import ExchangeSummary from './ExchangeSummary'

class App extends Component {
  state = {
    balances: [],
    isLoading: false
  };

  async componentDidMount() {
    const response = await fetch('/balances/today');
    const body = await response.json();
    this.setState({
      balances: body,
      isLoading: true
    });
  }

  groupBy(objectArray, property) {
    return objectArray.reduce(function (acc, obj) {
      let key = obj[property]
      if (!acc[key]) {
        acc[key] = []
      }
      acc[key].push(obj)
      return acc
    }, {})
  }

  render() {
    const {balances, isLoading} = this.state;

    if(!isLoading){
      return (
        <div>Loading...</div>
      );
    }

    const balancesByExchange = this.groupBy(balances, 'exchange');
    console.log(balancesByExchange);

    return (
        <div className="App">
          <header className="App-header">
            <div className="App-intro">
              <h1>Balances</h1>
              {Object.entries(balancesByExchange).map( ([key, value]) =>
                  <ExchangeSummary
                    key={key}
                    name={key}
                    balances={value}
                  />
              )}
            </div>
          </header>
        </div>
    );
  }
}
export default App;