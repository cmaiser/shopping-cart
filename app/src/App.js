import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ItemList from './ItemList';
import Cart from './Cart';
import ItemAdd from './ItemAdd';
import { CookiesProvider } from 'react-cookie';

class App extends Component {

    render() {
        return (
            <CookiesProvider>
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/items' exact={true} component={ItemList}/>
                    <Route path='/items/:id' component={ItemAdd}/>
                    <Route path='/cart' component={Cart}/>
                </Switch>
            </Router>
            </CookiesProvider>
        )
    }
}

export default App;
