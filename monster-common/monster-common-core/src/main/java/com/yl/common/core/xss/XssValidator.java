package com.yl.common.core.xss;

import com.yl.common.core.utils.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: YL
 * @Date: 2024-05-31
 * @Project monster
 */
public class XssValidator implements ConstraintValidator<Xss, String> {
    private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

    /**
     * 验证给定的字符串是否有效。
     * 该方法首先检查字符串是否为空或仅包含空白字符。如果是，则认为它是有效的。
     * 如果字符串包含非空白字符，则进一步检查它是否包含HTML代码。
     * 如果字符串不包含HTML代码，则认为它是有效的。
     *
     * @param value 待验证的字符串。
     * @param constraintValidatorContext 验证上下文，提供有关验证过程的上下文信息。
     * @return 如果字符串无效（即为空或包含HTML代码），则返回false；否则返回true。
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 首先检查字符串是否为空或仅包含空白字符
        if (StringUtils.isBlank(value)){
            return true;
        }
        // 进一步检查字符串是否包含HTML代码
        return !containsHtml(value);
    }

    /**
     * 检查给定的字符串是否包含HTML内容。
     *
     * @param value 需要检查的字符串。
     * @return 如果字符串包含HTML内容，则返回true；否则返回false。
     */
    private boolean containsHtml(String value) {
        // 编译HTML模式的正则表达式
        Pattern pattern = Pattern.compile(HTML_PATTERN);
        // 创建一个匹配器，用于匹配value字符串
        Matcher matcher = pattern.matcher(value);
        // 返回匹配结果，如果匹配成功则返回true，否则返回false
        return matcher.matches();
    }

}
