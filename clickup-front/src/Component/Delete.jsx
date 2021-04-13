import React, {Component,useState} from 'react';
import './Css/Delete.css';
import {DragDropContext, Draggable, Droppable} from "react-beautiful-dnd";
import _ from "lodash";
import {v4} from "uuid";
import {TOKEN} from "../utils/constants";
import axios from "axios";
import api from "../utils/api";
import '../utils/config'
import '../utils/constants'

const item = {
    id: v4(),
    name: "read the book"
}

const item2 = {
    id: v4(),
    name: "Wash the car"
}

 

function App() {
    const [text, setText] = useState("")
    const [state, setState] = useState({
        "todo": {
            title: "Todo",
            items: [item, item2]
        },
        "in-progress": {
            title: "In Progress",
            items: []
        },
        "done": {
            title: "Completed",
            items: []
        }
    })

    if (!localStorage.getItem(TOKEN)) {
        this.props.history.push('/home')
        // console.log(api)
    } else {
        this.getProjects()
    }

    // getProjects =()=> {
    //     console.log(localStorage.getItem(TOKEN))
    //     axios.get(api.projectUrl + '/findProjects', {
    //         headers: {
    //             // 'Content-Type': 'application/json',
    //             'Authorization': localStorage.getItem(TOKEN)
    //         }
    //     })
    //         .then(res => {
    //             // console.log(res.data)
    //             // this.setState(res.data.object)
    //
    //             this.setState({projects: res.data.object})
    //             console.log(this.setState({projects: res.data.object}))
    //
    //         }).catch(error => {
    //         console.log(error.data)
    //     })
    // }

    const handleDragEnd = ({destination, source}) => {
        if (!destination) {
            return
        }

        if (destination.index === source.index && destination.droppableId === source.droppableId) {
            return
        }

        // Creating a copy of item before removing it from state
        const itemCopy = {...state[source.droppableId].items[source.index]}

        setState(prev => {
            prev = {...prev}
            // Remove from previous items array
            prev[source.droppableId].items.splice(source.index, 1)


            // Adding to new items array location
            prev[destination.droppableId].items.splice(destination.index, 0, itemCopy)

            return prev
        })
    }

    const addItem = () => {
        setState(prev => {
            return {
                ...prev,
                todo: {
                    title: "Todo",
                    items: [
                        {
                            id: v4(),
                            name: text
                        },
                        ...prev.todo.items
                    ]
                }
            }
        })

        setText("")
    }

    return (
        <div className="Delete">
            <div className="task__add">
                <input type="text" value={text} onChange={(e) => setText(e.target.value)}/>
                <button className="btn-info" onClick={addItem}>Add task</button>
            </div>
            <DragDropContext onDragEnd={handleDragEnd}>
                {_.map(state, (data, key) => {
                    return(
                        <div key={key} className={"column"}>
                            <h3>{data.title}</h3>
                            <Droppable droppableId={key}>
                                {(provided, snapshot) => {
                                    return(
                                        <div
                                            ref={provided.innerRef}
                                            {...provided.droppableProps}
                                            className={"droppable-col"}
                                        >
                                            {data.items.map((el, index) => {
                                                return(
                                                    <Draggable key={el.id} index={index} draggableId={el.id}>
                                                        {(provided, snapshot) => {
                                                            console.log(snapshot)
                                                            return(
                                                                <div
                                                                    className={`item ${snapshot.isDragging && "dragging"}`}
                                                                    ref={provided.innerRef}
                                                                    {...provided.draggableProps}
                                                                    {...provided.dragHandleProps}
                                                                >
                                                                    {el.name}
                                                                </div>
                                                            )
                                                        }}
                                                    </Draggable>
                                                )
                                            })}
                                            {provided.placeholder}
                                        </div>
                                    )
                                }}
                            </Droppable>
                        </div>
                    )
                })}
            </DragDropContext>

        </div>
    );
}

export default App;
