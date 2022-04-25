import React, { Component } from 'react';
import { Spinner } from "reactstrap";
import { TabContent, TabPane, Nav, NavItem, NavLink } from 'reactstrap';
import classnames from 'classnames';
import Summary from './Summary'
import ExchangeSummary from './ExchangeSummary'

class App extends Component {
  constructor(props){
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      activeTab: '1',
      balances: [],
      isLoading: false
    };
  }

  toggle(tab) {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab
      });
    }
  }

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
        <div>
            <Spinner>
              Loading...
            </Spinner>
        </div>
      );
    }

    const balancesByExchange = this.groupBy(balances, 'exchange');

    return (
        <div className="App">
          <header className="App-header">

            <Nav tabs>
              <NavItem>
                <NavLink className={classnames({ active: this.state.activeTab === '1' })} onClick={() => { this.toggle('1'); }}>
                  Summary
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink className={classnames({ active: this.state.activeTab === '2' })} onClick={() => { this.toggle('2'); }}>
                  Exchange Summary
                </NavLink>
              </NavItem>
            </Nav>
          </header>


          <TabContent activeTab={this.state.activeTab}>
            <TabPane tabId="1">
              <Summary
                balancesByExchange={balancesByExchange}
              />
            </TabPane>
            <TabPane tabId="2">
              {Object.entries(balancesByExchange).map( ([key, value]) =>
                <ExchangeSummary
                  key={key}
                  name={key}
                  balances={value}
                />
              )}
            </TabPane>
          </TabContent>

        </div>
    );
  }
}
export default App;