import React, { Component } from 'react';
import { Spinner } from "reactstrap";
import { TabContent, TabPane, Nav, NavItem, NavLink } from 'reactstrap';
import classnames from 'classnames';
import Summary from './Summary'
import ExchangeSummary from './ExchangeSummary'

class App extends Component {
  constructor(props){
    super(props);

    this.state = {
      activeTab: '1',
      balances: [],
      isLoading: true
    };

    // This binding is necessary to make `this` work in the callback
    this.toggleTab = this.toggleTab.bind(this);
    this.deleteBalance = this.deleteBalance.bind(this);
  }

  async deleteBalance(balanceId){
      this.setState({
        isLoading: true
      });
      console.log('deleteBalance');
      const response = await fetch('/balances/' + balanceId, {
        method: 'DELETE',
      });
      if(response.status >= 200 && response.status <= 299){
        const newBalances = this.state.balances.filter(b => b.id !== balanceId);
        this.setState({
          balances: newBalances,
          isLoading: false
        });
      }
    }

  toggleTab(tab) {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab
      });
    }
  }

  async componentDidMount() {
    const response = await fetch('/balances/today');
    const body = await response.json();

    // remove all coin balances whose value in EUR is < 0.01
    const balances = body.filter(b => b.amount*b.pricePerUnit > 0.01);
    this.setState({
      balances: balances,
      isLoading: false
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

    if(isLoading){
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
                <NavLink className={classnames({ active: this.state.activeTab === '1' })} onClick={() => { this.toggleTab('1'); }}>
                  Summary
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink className={classnames({ active: this.state.activeTab === '2' })} onClick={() => { this.toggleTab('2'); }}>
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
                  onDeleteBalance={this.deleteBalance}
                />
              )}
            </TabPane>
          </TabContent>

        </div>
    );
  }
}
export default App;