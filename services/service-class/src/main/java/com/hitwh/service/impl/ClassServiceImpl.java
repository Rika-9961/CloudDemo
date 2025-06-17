package com.hitwh.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hitwh.dto.pojo.ClassDTO;
import com.hitwh.dto.vo.ClassListVO;
import com.hitwh.dto.vo.ClassVO;
import com.hitwh.entity.Class;
import com.hitwh.entity.Staff;
import com.hitwh.feign.StaffFeignClient;
import com.hitwh.repository.ClassRepository;
import com.hitwh.service.ClassService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Resource
    private ClassRepository classRepository;

    @Resource
    private StaffFeignClient staffFeignClient;

    @Resource
    private ModelMapper modelMapper;


    @Override
    public ClassListVO searchClass(String name, LocalDate startDate, LocalDate endDate,
                                   Pageable pageable) {
        Specification<Class> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("start"), startDate));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("end"), endDate));
            }
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        Long total = classRepository.count(specification);
        Page<ClassVO> map = classRepository.findAll(specification, pageable).map(ClassVO::new);
        return new ClassListVO(total, map.getContent());
    }

    @Override
    public ClassVO getClassById(Long id) {
        Optional<Class> optional = classRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("查无此人");
        }
        return new ClassVO(optional.get());
    }

    @Override
    public Class getClassByIdDirect(Long id) {
        Optional<Class> optional = classRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("查无此人");
        }
        return optional.get();
    }

    @Override
    public void deleteClass(Long id) {
        Optional<Class> optional = classRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            Class classes = optional.get();
            classes.setDeleted(true);
            classRepository.save(classes);
        }
    }

    @SentinelResource(value = "addClass")
    @Override
    public ClassVO addClass(ClassDTO classes) {
        Staff staff = modelMapper.map(staffFeignClient.show(classes.getTeacherId()).getBody(),
                Staff.class);
        if (staff == null) {
            throw new RuntimeException("老师不存在");
        }
        Class clazz = new Class();
        clazz.setName(classes.getName());
        clazz.setAddress(classes.getAddress());
        clazz.setStart(classes.getStart());
        clazz.setEnd(classes.getEnd());
        clazz.setTeacher(staff);
        Optional<Class> optional = classRepository.findByNameAndDeletedFalse(clazz.getName());
        if (optional.isPresent()) {
            throw new RuntimeException("班级已存在");
        }
        return new ClassVO(classRepository.save(clazz));
    }

    @Override
    public ClassVO updateClass(ClassDTO updateClass) {
        Optional<Class> optional = classRepository.findByIdAndDeletedFalse(updateClass.getId());
        if (optional.isEmpty()) {
            return null;
        }
        Class classes = optional.get();
        Optional.ofNullable(updateClass.getName()).ifPresent(classes::setName);
        Optional.ofNullable(updateClass.getAddress()).ifPresent(classes::setAddress);
        Optional.ofNullable(updateClass.getStart()).ifPresent(classes::setStart);
        Optional.ofNullable(updateClass.getEnd()).ifPresent(classes::setEnd);
        Optional.ofNullable(
                        modelMapper.map(staffFeignClient.show(updateClass.getTeacherId()), Staff.class))
                .ifPresent(classes::setTeacher);
        return new ClassVO(classRepository.save(classes));
    }

    @Override
    public Integer getTotalNumber() {
        return classRepository.findAllByDeletedFalse().size();
    }
}
