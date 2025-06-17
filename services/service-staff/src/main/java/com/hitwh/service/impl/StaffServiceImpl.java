package com.hitwh.service.impl;

import com.hitwh.dto.vo.StaffListVO;
import com.hitwh.dto.vo.StaffVO;
import com.hitwh.entity.Staff;
import com.hitwh.repository.StaffRepository;
import com.hitwh.service.StaffService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    @Resource
    private StaffRepository staffRepository;

    @Override
    public StaffListVO searchStaff(String name, String sex, LocalDate startDate, Pageable pageable) {
        Specification<Staff> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(sex)) {
                predicates.add(criteriaBuilder.equal(root.get("sex"), sex));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("start"), startDate));
            }
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
        });
        Long total = staffRepository.count(specification);
        Page<StaffVO> map = staffRepository.findAll(specification, pageable).map(StaffVO::new);
        return new StaffListVO(total, map.getContent());
    }

    @Override
    public StaffVO getStaffById(Long id) {
        Optional<Staff> optional = staffRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("查无此人");
        }
        return new StaffVO(optional.get());
    }

    @Override
    public Staff getStaffByIdDirect(Long id) {
        Optional<Staff> optional = staffRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("查无此人");
        }
        return optional.get();
    }

    @Override
    public void deleteStaff(Long id) {
        Optional<Staff> optional = staffRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            Staff staff = optional.get();
            staff.setDeleted(true);
            staff.setLastTime(LocalDateTime.now());
            staffRepository.save(staff);
        }
    }

    @Override
    public void deleteAllStaff(List<Long> ids) {
        List<Staff> staffs = staffRepository.findAllByIdInAndDeletedFalse(ids);
        for(Staff staff: staffs) {
            staff.setDeleted(true);
            staff.setLastTime(LocalDateTime.now());
        }
        staffRepository.saveAll(staffs);
    }

    @Override
    public StaffVO addStaff(Staff staff) {
        Optional<Staff> optional = staffRepository.findByNameAndDeletedFalse(staff.getName());
        if (optional.isPresent()) {
            throw new RuntimeException("员工已存在");
        }
        staff.setLastTime(LocalDateTime.now());
        return new StaffVO(staffRepository.save(staff));
    }

    @Override
    public StaffVO updateStaff(Staff updateStaff) {
        Optional<Staff> optional = staffRepository.findByIdAndDeletedFalse(updateStaff.getId());
        if (optional.isEmpty()) {
            return null;
        }
        Staff staff = optional.get();
        Optional.ofNullable(updateStaff.getName()).ifPresent(staff::setName);
        Optional.ofNullable(updateStaff.getStaffId()).ifPresent(staff::setStaffId);
//        Optional.ofNullable(updateStaff.getPicture()).ifPresent(staff::setPicture);
        Optional.ofNullable(updateStaff.getSex()).ifPresent(staff::setSex);
        Optional.ofNullable(updateStaff.getJob()).ifPresent(staff::setJob);
        Optional.ofNullable(updateStaff.getStart()).ifPresent(staff::setStart);
        Optional.ofNullable(updateStaff.getDepartment()).ifPresent(staff::setDepartment);
        Optional.ofNullable(updateStaff.getCourses()).ifPresent(staff::setCourses);
        staff.setLastTime(LocalDateTime.now());
        return new StaffVO(staffRepository.save(staff));
    }

    @Override
    public Integer getTotalNumber() {
        return staffRepository.findAllByDeletedFalse().size();
    }
}
