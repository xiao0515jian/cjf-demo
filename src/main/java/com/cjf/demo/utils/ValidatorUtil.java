package com.cjf.demo.utils;

//import faw.ba.ba0214.ishtar.common.constant.enums.ErrorCodeEnum;
//import faw.ba.ba0214.ishtar.model.base.IshtarException;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.validator.HibernateValidator;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.util.Set;
//
///**
// * @author : chenjianfeng
// * @date : 2022/11/22
// */
//@Slf4j
//public class ValidatorUtil {
//    private static final Validator VALIDATOR = Validation
//            .byProvider(HibernateValidator.class)
//            .configure()
//            .failFast(true)
//            .buildValidatorFactory()
//            .getValidator();
//    /**
//     * 实体校验
//     * @param obj 校验对象
//     */
//    public static <T> void validate(T obj) {
//
//        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(obj);
//
//        if (constraintViolations.size() > 0) {
//
//            ConstraintViolation<T> validateInfo = constraintViolations.iterator().next();
//
//            log.error(validateInfo.getMessage());
//
//            throw new IshtarException(ErrorCodeEnum.PARAMETER_ERROR.getCode(),validateInfo.getMessage());
//
//        }
//    }
//}
