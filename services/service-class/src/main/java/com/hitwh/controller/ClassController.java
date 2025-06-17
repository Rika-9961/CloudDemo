package com.hitwh.controller;


import com.hitwh.dto.pojo.ClassDTO;
import com.hitwh.dto.pojo.ClassListDTO;
import com.hitwh.dto.vo.ClassListVO;
import com.hitwh.dto.vo.ClassVO;
import com.hitwh.entity.Class;
import com.hitwh.service.ClassService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 班级管理
 */
@Slf4j
@RestController
@RequestMapping("/classes")
public class ClassController {
    @Resource
    private ClassService classService;


    /**
     * 班级查询
     *
     * @param classesListDTO 班级分页查询 {@link ClassListDTO}
     * @return 分页查询的班级信息
     */
    @PostMapping("/list")
    public ResponseEntity<ClassListVO> getPage(@RequestBody ClassListDTO classesListDTO) {
        Pageable pageable = PageRequest.of(classesListDTO.getPageNumber(), classesListDTO.getPageSize());
        ClassListVO classesList = classService.searchClass(
                classesListDTO.getName(),
                classesListDTO.getStart(),
                classesListDTO.getEnd(),
                pageable);
        return ResponseEntity.ok().body(classesList);
    }

    /**
     * 查询回显
     *
     * @param id 班级id
     * @return 该id班级信息
     */

    @GetMapping("/show/{id}")
    public ResponseEntity<ClassVO> show(@PathVariable Long id) {
        ClassVO classes = classService.getClassById(id);
        return ResponseEntity.status(HttpStatus.OK).body(classes);
    }

    /**
     * 跨服查询回显
     *
     * @param id 班级id
     * @return 该id班级信息
     */

    @GetMapping("/show_class/{id}")
    public ResponseEntity<Class> showClass(@PathVariable Long id) {
        Class classes = classService.getClassByIdDirect(id);
        return ResponseEntity.status(HttpStatus.OK).body(classes);
    }

    /**
     * 修改班级
     *
     * @param updateClass 修改的班级信息 {@link Class}
     * @return 修改后的班级信息
     */
    @PutMapping("/update")
    public ResponseEntity<ClassVO> update(@RequestBody ClassDTO updateClass) {
        ClassVO classes = classService.updateClass(updateClass);
        if (classes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(classes);
    }

    /**
     * 删除班级
     *
     * @param id 删除的班级id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("删除");
        classService.deleteClass(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增班级
     *
     * @param classes 班级信息 {@link Class}
     * @return 新增的班级信息
     */
    @PostMapping("/add")
    public ResponseEntity<ClassVO> add(@RequestBody ClassDTO classes) {
        ClassVO addClass = classService.addClass(classes);
        return ResponseEntity.status(HttpStatus.CREATED).body(addClass);
    }
}

