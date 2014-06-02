/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,  
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package xxx.yyy.framework.common.application.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * @author Hardy Ferentschik
 */
public class PatternValidator implements ConstraintValidator<Pattern, CharSequence> {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(PatternValidator.class);

	private java.util.regex.Pattern pattern;

	public void initialize(Pattern parameters) {
		Pattern.Flag flags[] = parameters.flags();
		int intFlag = 0;
		for ( Pattern.Flag flag : flags ) {
			intFlag = intFlag | flag.getValue();
		}

		try {
			pattern = java.util.regex.Pattern.compile( parameters.regexp(), intFlag );
		}
		catch ( PatternSyntaxException e ) {
			log.error(e.getMessage(), e );
		}
	}

	public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
		
		if(StringUtils.isBlank(value)){
			return true ;
		}
	
		Matcher m = pattern.matcher( value );
		return m.matches();
	}
}
