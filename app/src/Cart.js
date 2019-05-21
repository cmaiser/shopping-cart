import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';

class Cart extends Component {

    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {
            items: [],
            csrfToken: cookies.get('XSRF-TOKEN'),
            isLoading: true};
    }

    async componentDidMount() {

        document.title = 'Cart';

        this.setState({isLoading: true});

        fetch('api/cart', {credentials: 'include'})
            .then(response => response.json())
            .then(data => this.setState({items: data, isLoading: false}))
            .catch(() => this.props.history.push('/'));
    }

    calculateTotal() {

        const {items} = this.state;
        var total = 0;

        if(items == null || items.length == 0) {
            return total;
        }

        for(var i=0; i<items.length; i++) {
            total += items[i].price * items[i].carts[0].quantity;
        }

        return total;
    }

    render() {
        const {items, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const itemList = items.map(item => {
            return <tr key={item.id}>
                <td style={{whiteSpace: 'nowrap'}}>{item.name}</td>
                <td style={{whiteSpace: 'nowrap'}}>{item.description}</td>
                <td style={{whiteSpace: 'nowrap'}}>{"$" + item.price.toFixed(2)}</td>
                <td style={{whiteSpace: 'nowrap'}}>{item.carts[0].quantity}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/items/" + item.id}>Update Quantity</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/items">Return to Store</Button>
                    </div>
                    <h3>Items for Sale</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Description</th>
                            <th width="20%">Price (each)</th>
                            <th width="20%">Quantity</th>
                        </tr>

                        </thead>
                        <tbody>
                        {itemList}
                        </tbody>
                    </Table>
                    <div>{"Total: $" + this.calculateTotal().toFixed(2)}</div>
                </Container>
            </div>
        );
    };


}

export default withCookies(withRouter(Cart));