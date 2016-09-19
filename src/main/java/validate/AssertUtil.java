package validate;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Assert工具
 *
 * @author zengxm
 * @date 2015年7月13日
 *
 */
public abstract class AssertUtil {

	/**
	 * Assert a boolean expression, throwing
	 * <code>IllegalArgumentException</code> if the test result is
	 * <code>false</code>.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if expression is <code>false</code>
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing
	 * <code>IllegalArgumentException</code> if the test result is
	 * <code>false</code>.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0);
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @throws IllegalArgumentException
	 *             if expression is <code>false</code>
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * Assert that an object is <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.isNull(value, &quot;The value must be null&quot;);
	 * </pre>
	 * 
	 * @param object
	 *            the object to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if the object is not <code>null</code>
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an object is <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.isNull(value);
	 * </pre>
	 * 
	 * @param object
	 *            the object to check
	 * @throws IllegalArgumentException
	 *             if the object is not <code>null</code>
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * Assert that an object is not <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz, &quot;The class must not be null&quot;);
	 * </pre>
	 * 
	 * @param object
	 *            the object to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if the object is <code>null</code>
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an object is not <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz);
	 * </pre>
	 * 
	 * @param object
	 *            the object to check
	 * @throws IllegalArgumentException
	 *             if the object is <code>null</code>
	 */
	public static void notNull(Object object) {
		notNull(object,
				"[Assertion failed] - this argument is required; it must not be null");
	}

	/**
	 * Assert that the given String is not empty; that is, it must not be
	 * <code>null</code> and not the empty String.
	 * 
	 * <pre class="code">
	 * Assert.hasLength(name, &quot;Name must not be empty&quot;);
	 * </pre>
	 * 
	 * @param text
	 *            the String to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @see StringUtils#hasLength
	 */
	public static void hasLength(String text, String message) {
		if (StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that the given String is not empty; that is, it must not be
	 * <code>null</code> and not the empty String.
	 * 
	 * <pre class="code">
	 * Assert.hasLength(name);
	 * </pre>
	 * 
	 * @param text
	 *            the String to check
	 * @see StringUtils#hasLength
	 */
	public static void hasLength(String text) {
		hasLength(
				text,
				"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	/**
	 * Assert that the given String has valid text content; that is, it must not
	 * be <code>null</code> and must contain at least one non-whitespace
	 * character.
	 * 
	 * <pre class="code">
	 * Assert.hasText(name, &quot;'name' must not be empty&quot;);
	 * </pre>
	 * 
	 * @param text
	 *            the String to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @see StringUtils#hasText
	 */
	public static void hasText(String text, String message) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that the given String has valid text content; that is, it must not
	 * be <code>null</code> and must contain at least one non-whitespace
	 * character.
	 * 
	 * <pre class="code">
	 * Assert.hasText(name, &quot;'name' must not be empty&quot;);
	 * </pre>
	 * 
	 * @param text
	 *            the String to check
	 * @see StringUtils#hasText
	 */
	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * 
	 * <pre class="code">
	 * Assert.doesNotContain(name, &quot;rod&quot;, &quot;Name must not contain 'rod'&quot;);
	 * </pre>
	 * 
	 * @param textToSearch
	 *            the text to search
	 * @param substring
	 *            the substring to find within the text
	 * @param message
	 *            the exception message to use if the assertion fails
	 */
	public static void doesNotContain(String textToSearch, String substring,
			String message) {
		if (StringUtils.contains(textToSearch, substring)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * 
	 * <pre class="code">
	 * Assert.doesNotContain(name, &quot;rod&quot;);
	 * </pre>
	 * 
	 * @param textToSearch
	 *            the text to search
	 * @param substring
	 *            the substring to find within the text
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring ["
						+ substring + "]");
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(array, &quot;The array must have elements&quot;);
	 * </pre>
	 * 
	 * @param array
	 *            the array to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if the object array is <code>null</code> or has no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ArrayUtils.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(array);
	 * </pre>
	 * 
	 * @param array
	 *            the array to check
	 * @throws IllegalArgumentException
	 *             if the object array is <code>null</code> or has no elements
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(
				array,
				"[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that an array has no null elements. Note: Does not complain if the
	 * array is empty!
	 * 
	 * <pre class="code">
	 * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
	 * </pre>
	 * 
	 * @param array
	 *            the array to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if the object array contains a <code>null</code> element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	/**
	 * Assert that an array has no null elements. Note: Does not complain if the
	 * array is empty!
	 * 
	 * <pre class="code">
	 * Assert.noNullElements(array);
	 * </pre>
	 * 
	 * @param array
	 *            the array to check
	 * @throws IllegalArgumentException
	 *             if the object array contains a <code>null</code> element
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array,
				"[Assertion failed] - this array must not contain any null elements");
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
	 * </pre>
	 * 
	 * @param collection
	 *            the collection to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if the collection is <code>null</code> or has no elements
	 */
	public static <E> void notEmpty(Collection<E> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
	 * </pre>
	 * 
	 * @param collection
	 *            the collection to check
	 * @throws IllegalArgumentException
	 *             if the collection is <code>null</code> or has no elements
	 */
	public static <E> void notEmpty(Collection<E> collection) {
		notEmpty(
				collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(map, &quot;Map must have entries&quot;);
	 * </pre>
	 * 
	 * @param map
	 *            the map to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalArgumentException
	 *             if the map is <code>null</code> or has no entries
	 */
	public static <K, V> void notEmpty(Map<K, V> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(map);
	 * </pre>
	 * 
	 * @param map
	 *            the map to check
	 * @throws IllegalArgumentException
	 *             if the map is <code>null</code> or has no entries
	 */
	public static <K, V> void notEmpty(Map<K, V> map) {
		notEmpty(
				map,
				"[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * 
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo);
	 * </pre>
	 * 
	 * @param clazz
	 *            the required class
	 * @param obj
	 *            the object to check
	 * @throws IllegalArgumentException
	 *             if the object is not an instance of clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * 
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo);
	 * </pre>
	 * 
	 * @param type
	 *            the type to check against
	 * @param obj
	 *            the object to check
	 * @param message
	 *            a message which will be prepended to the message produced by
	 *            the function itself, and which may be used to provide context.
	 *            It should normally end in a ": " or ". " so that the function
	 *            generate message looks ok when prepended to it.
	 * @throws IllegalArgumentException
	 *             if the object is not an instance of clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(message + " Object of class ["
					+ (obj != null ? obj.getClass().getName() : "null")
					+ "] must be an instance of " + type);
		}
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is
	 * <code>true</code>.
	 * 
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass);
	 * </pre>
	 * 
	 * @param superType
	 *            the super type to check
	 * @param subType
	 *            the sub type to check
	 * @throws IllegalArgumentException
	 *             if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is
	 * <code>true</code>.
	 * 
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass);
	 * </pre>
	 * 
	 * @param superType
	 *            the super type to check against
	 * @param subType
	 *            the sub type to check
	 * @param message
	 *            a message which will be prepended to the message produced by
	 *            the function itself, and which may be used to provide context.
	 *            It should normally end in a ": " or ". " so that the function
	 *            generate message looks ok when prepended to it.
	 * @throws IllegalArgumentException
	 *             if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType,
			String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(message + subType
					+ " is not assignable to " + superType);
		}
	}

	/**
	 * Assert a boolean expression, throwing <code>IllegalStateException</code>
	 * if the test result is <code>false</code>. Call isTrue if you wish to
	 * throw IllegalArgumentException on an assertion failure.
	 * 
	 * <pre class="code">
	 * Assert.state(id == null, &quot;The id property must not already be initialized&quot;);
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalStateException
	 *             if expression is <code>false</code>
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing {@link IllegalStateException} if
	 * the test result is <code>false</code>.
	 * <p>
	 * Call {@link #isTrue(boolean)} if you wish to throw
	 * {@link IllegalArgumentException} on an assertion failure.
	 * 
	 * <pre class="code">
	 * Assert.state(id == null);
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @throws IllegalStateException
	 *             if the supplied expression is <code>false</code>
	 */
	public static void state(boolean expression) {
		state(expression,
				"[Assertion failed] - this state invariant must be true");
	}

	/**
	 * 限定参数的长度不能超过指定值，否则抛出异常。 当 s 不等于null，并且长度超出maxLength才抛出异常。
	 * 
	 * @param str
	 *            - 要判断的字符串
	 * @param maxLength
	 *            - 指定长度
	 * @param message
	 *            - 出错时的异常错误信息
	 * @throws IllegalArgumentException
	 *             - 出错时抛出异常
	 */
	public static void assertMaxLength(String str, int maxLength, String message) {
		if (StringUtils.isEmpty(str) || str.length() > maxLength) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 限定一个整数不能是负数，否则抛出
	 * 
	 * @param n
	 *            - 要判断的整数
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertNonnegativeInt(int n, String errorMessage) {
		if (n < 0) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 限定a必须大于等于b，否则抛出
	 * 
	 * @param a
	 * @param b
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertGreaterThanOrEqual(int a, int b,
			String errorMessage) {
		if (a < b) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 限定a必须等于b，否则抛出
	 * 
	 * @param l
	 * @param m
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertEqual(long l, long m, String errorMessage) {
		if (l != m) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 限定a必须大于等于b，否则抛出
	 * 
	 * @param a
	 * @param b
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertGreaterThanOrEqual(long a, long b,
			String errorMessage) {
		if (a < b) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 限定输入值必须是指定列表的其中之一，否则抛出
	 * 
	 * @param a
	 *            - 输入值
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @param values
	 *            - 指定列表
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertOneOfThem(short a, String errorMessage,
			short... values) {
		boolean pass = false;
		if (values != null) {
			for (short n : values) {
				if (a == n) {
					pass = true;
					break;
				}
			}
		} else {
			throw new IllegalArgumentException("values为空");
		}
		if (!pass) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 判断参数必须大于 0，否则抛出异常
	 * 
	 * @param l
	 *            - 要判断的数字
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertGreaterThanZero(long l, String errorMessage) {
		if (l < 1) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 判断参数列表里必须有一个大于0
	 * 
	 * @param values
	 *            - 要判断的数字组合
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertOneOfThemThanZero(String errorMessage,
			int... values) {
		boolean exception = true;
		if (values != null) {
			for (int n : values) {
				if (n >= 0) {
					exception = false;
					break;
				}
			}
		}
		if (exception) {
			throw new IllegalArgumentException(errorMessage);
		}

	}

	/**
	 * 判断参数不能为null，否则抛出异常
	 * 
	 * @param o
	 *            - 要判断的参数
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertNotNull(Object o, String errorMessage) {
		if (o == null) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * 判断时间范围的合法性，end>=start，否则抛出异常
	 * 
	 * @param start
	 *            - 开始时间，不能为null
	 * @param end
	 *            - 结束时间，不能为null
	 * @param errorMessage
	 *            - 出错时的异常错误信息
	 * @throws 
	 *             - 出错时抛出异常
	 */
	public static void assertTimeRange(Date start, Date end, String errorMessage) {
		if (start == null || end == null) {
			throw new IllegalArgumentException("start或end为空");
		}
		if (start.after(end)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

}
