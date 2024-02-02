import { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPencil } from '@fortawesome/free-solid-svg-icons';


const API_BASE = "http://localhost:8080/api";

function App() {
  const [todos, setTodos] = useState([]);
  const [popupActive, setPopupActive] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [editedTodo, setEditedTodo] = useState(null);
  const [newTitle, setNewTitle] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [newDueDate, setNewDueDate] = useState(new Date());
  const [newDateAdded, setNewDateAdded] = useState(new Date());

  useEffect(() => {
    getTodos();
  }, []);

  const getTodos = async () => {
    try {
      const response = await fetch(`${API_BASE}/todo`);
      if (!response.ok) {
        throw new Error("Failed to fetch todos");
      }
      const data = await response.json();
      setTodos(data);
    } catch (err) {
      console.error("Error: ", err);
    }
  };

  const completeTodo = async (id, isCompleted) => {
    try {
      const response = await fetch(`${API_BASE}/todo/${id}/complete`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ id }),
      });
      if (!response.ok) {
        throw new Error("Failed to complete todo");
      }
      const updatedTodos = todos.map((todo) =>
        todo.id === id ? { ...todo, isCompleted: !isCompleted } : todo
      );
      setTodos(updatedTodos);
    } catch (err) {
      console.error("Error: ", err);
    }
  };

  const deleteTodo = async (id) => {
    try {
      const response = await fetch(`${API_BASE}/todo/${id}`, {
        method: "DELETE",
      });
      if (!response.ok) {
        throw new Error("Failed to delete todo");
      }
      const updatedTodos = todos.filter((todo) => todo.id !== id);
      setTodos(updatedTodos);
    } catch (err) {
      console.error("Error: ", err);
    }
  };

  const addTodo = async () => {
    try {
      const response = await fetch(`${API_BASE}/todo`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: newTitle,
          description: newDescription,
          due_date: newDueDate,
          date_added: newDateAdded,
          isCompleted: false
        }),
      });
      if (!response.ok) {
        throw new Error("Failed to add todo");
      }
      const data = await response.json();
      setTodos((prevTodos) => [...prevTodos, data]);
      setPopupActive(false);
      setNewTitle("");
      setNewDescription("");
      setNewDueDate(new Date());
      setNewDateAdded(new Date());
    } catch (err) {
      console.error("Error: ", err);
    }
  };

  const editTodo = async () => {
    try {
      const response = await fetch(`${API_BASE}/todo/${editedTodo.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: newTitle || editedTodo.title,
          description: newDescription || editedTodo.description,
          due_date: newDueDate !== editedTodo.due_date ? newDueDate : editedTodo.due_date,
          date_added: newDateAdded !== editedTodo.date_added ? newDateAdded : editedTodo.date_added,
          isCompleted: editedTodo.isCompleted
        }),
      });
      if (!response.ok) {
        throw new Error("Failed to edit todo");
      }
      const updatedTodos = todos.map(todo =>
        todo.id === editedTodo.id ? {
          ...todo,
          title: newTitle || editedTodo.title,
          description: newDescription || editedTodo.description,
          due_date: newDueDate !== editedTodo.due_date ? newDueDate : editedTodo.due_date,
          date_added: newDateAdded !== editedTodo.date_added ? newDateAdded : editedTodo.date_added
        } : todo
      );
      setTodos(updatedTodos);
      setEditMode(false);
      setPopupActive(false);
      setEditedTodo(null);
      setNewTitle("");
      setNewDescription("");
      setNewDueDate(new Date());
      setNewDateAdded(new Date());
    } catch (err) {
      console.error("Error: ", err);
    }
  };


  const handleEditClick = (todo) => {
    setPopupActive(true);
    setEditMode(true);
    setEditedTodo(todo);
    setNewTitle(todo.title);
    setNewDescription(todo.description);
    setNewDueDate(new Date(todo.due_date));
    setNewDateAdded(new Date(todo.date_added));
  };

  return (
    <div className="App">
      <h1>Welcome, Om</h1>
      <h4>Your Tasks</h4>

      <div className="todos">
        {todos.map((todo) => (
          <div key={todo.id}
            className={"todo " + (todo.isCompleted ? "is-complete" : "")}
          >
            <div className="checkbox" onClick={() => completeTodo(todo.id, todo.isCompleted)}></div>

            <div className="container">
              <div className="text title">{todo.title}</div>
              <div className="text description">{todo.description}</div>
            </div>
            <div className="date">{new Date(todo.due_date).toLocaleDateString()}</div>

            <div className="edit-todo" onClick={() => handleEditClick(todo)}>
            <FontAwesomeIcon icon={faPencil} />
            </div>
            <div className="delete-todo" onClick={() => deleteTodo(todo.id)}>
              x
            </div>
          </div>
        ))}
      </div>
      <div className="addPopup" onClick={() => setPopupActive(true)}>
        +
      </div>
      {popupActive && (
        <div className="popup">
          <div className="closePopup" onClick={() => setPopupActive(false)}>
            x
          </div>
          <div className="content">
            <h3>{editMode ? "Edit Task" : "Add Task"}</h3>
            <h4>Title
              <input
                type="text"
                className="add-todo-input"
                onChange={(e) => setNewTitle(e.target.value)}
                value={newTitle}
              />
            </h4>
            <h4>Description
              <input
                type="text"
                className="add-todo-input"
                onChange={(e) => setNewDescription(e.target.value)}
                value={newDescription}
              />
            </h4>
            <h4>Date Due
              <DatePicker
                selected={newDueDate}
                onChange={(date) => setNewDueDate(date)}
                className="add-todo-input"
                dateFormat="MM/dd/yyyy"
              />
            </h4>
            <div className="button" onClick={editMode ? editTodo : addTodo}>
              {editMode ? "Update Task" : "Create Task"}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
