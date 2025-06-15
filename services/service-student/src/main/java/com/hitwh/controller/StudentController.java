package com.hitwh.controller;

import com.webdemo.controller.pojo.StudentDTO;
import com.webdemo.controller.pojo.StudentListDTO;
import com.webdemo.controller.vo.StudentListVO;
import com.webdemo.controller.vo.StudentVO;
import com.webdemo.entity.Student;
import com.webdemo.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学员管理
 */

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    /**
     * 学员查询
     *
     * @param studentListDTO 学员信息分页查询 {@link StudentListDTO}
     * @return 分页查询的学员信息
     */
    @PostMapping("/list")
    public ResponseEntity<StudentListVO> getPage(@RequestBody StudentListDTO studentListDTO) {
        Pageable pageable = PageRequest.of(studentListDTO.getPageNumber(), studentListDTO.getPageSize());
        StudentListVO studentList = studentService.searchStudent(
                studentListDTO.getName(),
                studentListDTO.getStudentId(),
                studentListDTO.getClassName(),
                studentListDTO.getEducationBackground(),
                pageable);
        return ResponseEntity.ok().body(studentList);
    }

    /**
     * 查询回显
     *
     * @param id 学员id
     * @return 该id学员信息
     */

    @GetMapping("/show/{id}")
    public ResponseEntity<StudentVO> show(@PathVariable Long id) {
        StudentVO student = studentService.getStudentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    /**
     * 修改学员
     *
     * @param updateStudent 修改的学员信息 {@link Student}
     * @return 修改后的学员信息
     */
    @PutMapping("/update")
    public ResponseEntity<StudentVO> update(@RequestBody StudentDTO updateStudent) {
        StudentVO student = studentService.updateStudent(updateStudent);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(student);
    }

    /**
     * 删除学员
     *
     * @param id 删除的学员id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("删除");
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 批量删除学员
     *
     * @param ids 删除的学员id
     */
    @DeleteMapping("/delete_all")
    public ResponseEntity<Void> deleteAll(@RequestBody List<Long> ids) {
        log.info("删除");
        studentService.deleteAllStudent(ids);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增学员
     *
     * @param student 学员信息 {@link Student}
     * @return 新增的学员信息
     */
    @PostMapping("/add")
    public ResponseEntity<StudentVO> add(@RequestBody StudentDTO student) {
        StudentVO addStudent = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(addStudent);
    }

    /**
     * 违纪处理
     *
     * @param id    违纪学员id
     * @param score 违纪扣分分数
     */

    @PutMapping("/violate")
    public ResponseEntity<Void> violate(@RequestParam Long id, @RequestParam Double score) {
        studentService.violateStudent(score, id);
        return ResponseEntity.ok().build();
    }


}
