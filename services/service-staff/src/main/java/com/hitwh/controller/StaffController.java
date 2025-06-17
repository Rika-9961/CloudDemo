package com.hitwh.controller;

import com.hitwh.dto.pojo.StaffListDTO;
import com.hitwh.dto.vo.StaffListVO;
import com.hitwh.dto.vo.StaffVO;
import com.hitwh.entity.Staff;
import com.hitwh.service.StaffService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffController {
    @Resource
    private StaffService staffService;

    /**
     * 员工查询
     *
     * @param staffListDTO 员工信息分页 {@link StaffListDTO}
     * @return 员工分页信息
     */
    @PostMapping("/list")
    public ResponseEntity<StaffListVO> getPage(@RequestBody StaffListDTO staffListDTO) {
        Pageable pageable = PageRequest.of(staffListDTO.getPageNumber(), staffListDTO.getPageSize());
        StaffListVO staffList = staffService.searchStaff(
                staffListDTO.getName(),
                staffListDTO.getSex(),
                staffListDTO.getStart(),
                pageable);
        return ResponseEntity.ok().body(staffList);
    }

    /**
     * 查询回显
     *
     * @param id 员工id
     * @return 该id员工信息
     */

    @GetMapping("/show/{id}")
    public ResponseEntity<StaffVO> show(@PathVariable Long id) {
        StaffVO staff = staffService.getStaffById(id);
        return ResponseEntity.status(HttpStatus.OK).body(staff);
    }

    /**
     * 查询回显
     *
     * @param id 员工id
     * @return 该id员工信息
     */

    @GetMapping("/show_staff/{id}")
    public ResponseEntity<Staff> showStaff(@PathVariable Long id) throws InterruptedException {
        Staff staff = staffService.getStaffByIdDirect(id);
//        Thread.sleep(2000000);
        return ResponseEntity.status(HttpStatus.OK).body(staff);
    }

    /**
     * 修改员工
     *
     * @param updateStaff 修改员工信息 {@link Staff}
     * @return 修改后的员工信息
     */
    @PutMapping("/update")
    public ResponseEntity<StaffVO> update(@RequestBody Staff updateStaff) {
        StaffVO staff = staffService.updateStaff(updateStaff);
        if (staff == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(staff);
    }

    /**
     * 删除员工
     *
     * @param id 删除的员工id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("删除");
        staffService.deleteStaff(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 批量删除员工
     *
     * @param ids 删除的员工id
     */
    @DeleteMapping("/delete_all")
    public ResponseEntity<Void> deleteAll(@RequestBody List<Long> ids) {
        log.info("删除");
        staffService.deleteAllStaff(ids);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增员工
     *
     * @param staff 员工信息 {@link Staff}
     * @return 新增的员工信息
     */
    @PostMapping("/add")
    public ResponseEntity<StaffVO> add(@RequestBody Staff staff) {
        StaffVO addStaff = staffService.addStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(addStaff);
    }
}
