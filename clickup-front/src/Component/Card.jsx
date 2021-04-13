import React, {useState, Component} from 'react'


function App() {

    const [boards, setBoards] = useState([
        {
            id: 1,
            title: "Сделать",
            items: [{id: 1, title: 'Пойти в магазин'}, {id: 2, title: 'Выкинуть мусор'}, {id: 3, title: 'Купить хлеб'}]
        },
        {
            id: 2,
            title: "Проверить",
            items: [{id: 4, title: 'Код ревю'}, {id: 5, title: 'Задача на факториал'}, {id: 6, title: 'Выкинуть мусор'}]
        },
        {id: 3, title: "Сделано", items: [{id: 7, title: 'ремонт'}, {id: 8, title: 'Смонтировать'}]}
    ])


    const [currentBoard, setCurrentBoard] = useState(null);
    const [currentItem, setCurrentItem] = useState(null);


    function dragStartHandler(e, board, item) {
        console.log(board, item)
        setCurrentBoard(board)
        setCurrentItem(item)


    }

    function dragLeaveHandler(e) {
        e.target.style.boxShadow = 'none'


    }

    function dragEndHandlerr(e) {
        e.target.style.boxShadow = 'none'


    }

    function dragOverHandler(e) {
        e.preventDefault()
        if (e.target.className == 'item') {
            e.target.style.boxShadow = '0 2px 3px grey'
        }


    }

    function dropHandler(e, board, item) {
        e.preventDefault()
        const currentIndex = currentBoard.items.indexOf(currentItem)
        currentBoard.items.splice(currentIndex, 1)

        const dropIndex = board.items.indexOf(item)
        board.items.splice(dropIndex + 1, 0, currentItem)

        setBoards(boards.map(b => {
            if (b.id === boards.id) {
                return board
            }

            if (b.id === currentBoard.id) {
                return currentBoard
            }

            return b


        }))
    }

    function dropCardHandler(e, board) {


    }

    return (
        <div className="app">
            {boards.map(board =>
                <div className="board"
                     onDragOver={(e)=> dragOverHandler(e)}
                     onDrop={(e)=> dropCardHandler(e,board)}
                >
                    <div className="board__title">{board.title}</div>
                    {board.items.map(item =>
                        <div
                            onDragStart={(e) => dragStartHandler(e, board, item)}
                            onDragLeave={(e) => dragLeaveHandler(e)}
                            onDragEnd={(e) => dragEndHandlerr(e)}
                            onDragOver={(e) => dragOverHandler(e)}
                            onDrop={(e) => dropHandler(e, board,item)}

                            draggable="true"
                            className="item"

                        >{item.title}</div>
                    )}
                </div>
            )}
        </div>
    );
}

export default App;
