package com.bridgeit.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bridgeit.entity.User;

/**
 * @author Ajit Shikalgar
 *
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object userObject, ConstraintValidatorContext context) {
		User user = (User) userObject;
		if (user.getPassword().equals(user.getConfirmPassword()))
			return true;
		return false;
	}

}
