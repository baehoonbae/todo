package com.ssafy.edu.todo.mapper;

import com.ssafy.edu.todo.model.Todo;
import com.ssafy.edu.todo.model.TodoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface TodoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    long countByExample(TodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int deleteByExample(TodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int insert(Todo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int insertSelective(Todo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    List<Todo> selectByExampleWithRowbounds(TodoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    List<Todo> selectByExample(TodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    Todo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") Todo row, @Param("example") TodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") Todo row, @Param("example") TodoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Todo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table todo..todo
     *
     * @mbg.generated
     */
     int updateByPrimaryKey(Todo row);
     
     List<Todo> selectTodoByUserSeq(int userSeq);

    Todo selectTodoById(int todoId);
    
    int updateSelective(Todo todo);
}