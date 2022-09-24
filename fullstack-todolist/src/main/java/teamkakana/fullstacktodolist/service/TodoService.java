package teamkakana.fullstacktodolist.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import teamkakana.fullstacktodolist.dto.TodoDTO;
import teamkakana.fullstacktodolist.entity.TodoListEntity;
import teamkakana.fullstacktodolist.model.Todo;
import teamkakana.fullstacktodolist.repository.TodoRepository;
import teamkakana.fullstacktodolist.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final DateTimeUtil dateTimeUtil;
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;
    public List<TodoDTO> addTodo(@NonNull String newTodo){
        //initialize new to do
        TodoListEntity todo =TodoListEntity.builder()
                .todoId(UUID.randomUUID())
                .todo(newTodo)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

//        save to database
        todoRepository.save(todo);


        return getAllTodos();
    }
    public List<TodoDTO> updateTodo(@NonNull Todo updatedTodo) {
//        get to do entity
        TodoListEntity oldTodo = todoRepository.findByTodoId(updatedTodo.getTodoId());
//        check if todoId exist
        if (oldTodo == null) throw new RuntimeException("Todo doesn't exist");

//        updatedTodo
        TodoListEntity newTodo = TodoListEntity.builder()
                .todoId(oldTodo.getTodoId())
                .todo(updatedTodo.getTodo())
                .createdDate(oldTodo.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();
        todoRepository.save(newTodo);
        return getAllTodos();
    }
    public List<TodoDTO> deleteTodo(UUID id){
//        get to do entity
        TodoListEntity todo = todoRepository.findByTodoId(id);

//        check if to do exist
        if (todo == null)  throw new RuntimeException("Todo doesn't exist");
//        delete to do
        todoRepository.deleteByTodoId(id);
        return getAllTodos();
    }
    private List<TodoDTO> getAllTodos(){

        List<TodoListEntity> allTodos = todoRepository.findAll();
        List<TodoDTO> updatedList = new ArrayList<>();

        allTodos.forEach(data ->{
            updatedList.add(modelMapper.map(data, TodoDTO.class));
        });
        return updatedList;
    }
}
