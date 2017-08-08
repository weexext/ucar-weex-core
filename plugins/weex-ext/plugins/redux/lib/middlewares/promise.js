const isPromise = val => val && React.Ext.utils.isFunction(val.then);


const promiseMiddleware = store => next => action => {
    const { dispatch } = store;
    return isPromise(action.payload) ? action.payload.then(
        result => dispatch({ ...action, payload: result }),
        error => dispatch({ ...action, payload: error, error: true })
    ) : next(action);
};

export default promiseMiddleware