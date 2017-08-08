const HEADER_HEIGHT = 44;
const STATUS_BAR_HEIGHT = 20;
const SIDE_GAP = 10;

export default {
    navBar: {
        paddingTop: STATUS_BAR_HEIGHT,
        backgroundColor: '#1ba9ba',
        position: 'relative',
    },
        header: {
            height: HEADER_HEIGHT,
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
            position: 'relative',
            backgroundColor: 'transparent',
        },
        actualHeader: {
            position: 'absolute',
            top: STATUS_BAR_HEIGHT,
            left: 0,
            right: 0,
        },
            headerItemCenter: {
                position: 'absolute',
                top: 0,
                bottom: 0,
                flexDirection: 'column',
                justifyContent: 'center',
                overflow: 'hidden',
            },
            headerItemLeft: {
                marginLeft: SIDE_GAP,
            },
            headerItemRight: {
                marginRight: SIDE_GAP,
            },

            headerTextCenter: {
                textAlign: 'center',
                fontWeight: 'bold',
                fontSize: 16,
            },

    // test: {
    //     borderColor: 'red',
    //     borderWidth: 1,
    // },
};
