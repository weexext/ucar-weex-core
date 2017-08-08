const { Component, PropTypes, Children } = React;
const storeShape = PropTypes.shape({
    subscribe: PropTypes.func.isRequired,
    dispatch: PropTypes.func.isRequired,
    getState: PropTypes.func.isRequired
});

class Provider extends Component {
    getChildContext() {
        return { store: this.store }
    }

    constructor(props, context) {
        super(props, context)
        this.store = props.store
    }

    render() {
        return Children.only(this.props.children)
    }
}

Provider.propTypes = {
    store: storeShape.isRequired,
    children: PropTypes.element.isRequired
};
Provider.childContextTypes = {
    store: storeShape.isRequired
};

export default Provider