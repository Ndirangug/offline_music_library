package annotations


/**
 * Annotates a test function used just to test out some api (probably 3rd party) before using it in production
 *
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class LearningTest

