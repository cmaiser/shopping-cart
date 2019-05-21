import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';

class ItemList extends Component {

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

        document.title = 'Items for Sale';

        this.setState({isLoading: true});

        fetch('api/allItems', {credentials: 'include'})
            .then(response => response.json())
            .then(data => this.setState({items: data, isLoading: false}))
            .catch(() => this.props.history.push('/'));
    }

    countItems() {

        const {items} = this.state;
        var total = 0;

        if(items == null || items.length == 0) {
            return total;
        }

        for(var i=0; i<items.length; i++) {
            if(items[i].carts.length != 0) {
                total += items[i].carts[0].quantity;
            }
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
             <td>
                 <ButtonGroup>
                     <Button size="sm" color="primary" tag={Link} to={"/items/" + (item.id == null ? "new" : item.id)}>Add to Cart</Button>
                 </ButtonGroup>
             </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                <div className="float-right">
                    {this.countItems() == 0 ? "Your cart is empty! " : this.countItems() + " Item(s) "}
                    <Button color="success" tag={Link} to="/cart">View Cart</Button>
                </div>
                    <h3>Items for Sale</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="20%">Name</th>
                                <th width="20%">Description</th>
                                <th width="20%">Price</th>
                            </tr>

                        </thead>
                        <tbody>
                            {itemList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    };


}

export default withCookies(withRouter(ItemList));