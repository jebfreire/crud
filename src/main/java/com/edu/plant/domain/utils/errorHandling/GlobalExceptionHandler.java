package com.edu.plant.domain.utils.errorHandling;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle javax.persistence.EntityNotFoundException
     */
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleJakartaEntityNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex)),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
            ex,
            buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex)),
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(NoResultException.class)
    protected ResponseEntity<Object> handlNoResultException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex)),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    protected ResponseEntity<Object> handleEntityAlreadyExists(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.PRECONDITION_FAILED);
        apiError.setMessage(ex.getMessage());
        return handleExceptionInternal(
                ex,
                buildResponseEntity(new ApiError(HttpStatus.PRECONDITION_FAILED, ex)),
                new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
//        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
//        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return handleExceptionInternal(
                ex,
                buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex)),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }





//    /**
//     * Handle NoHandlerFoundException.
//     *
//     * @param ex
//     * @param headers
//     * @param status
//     * @param request
//     * @return
//     */
////    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(
//            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
//        apiError.setDebugMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }



//    /**
//     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
//     *
//     * @param ex      MissingServletRequestParameterException
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
////    @Override
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(
//            MissingServletRequestParameterException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//        String error = ex.getParameterName() + " parameter is missing";
//        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
//    }


//    /**
//     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
//     *
//     * @param ex      HttpMediaTypeNotSupportedException
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
//            HttpMediaTypeNotSupportedException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        StringBuilder builder = new StringBuilder();
//        builder.append(ex.getContentType());
//        builder.append(" media type is not supported. Supported media types are ");
//        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
//        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
//    }
//

//    }

//    /**
//     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
//     *
//     * @param ex the ConstraintViolationException
//     * @return the ApiError object
//     */
//    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
//    protected ResponseEntity<Object> handleConstraintViolation(
//            javax.validation.ConstraintViolationException ex) {
//        ApiError apiError = new ApiError(BAD_REQUEST);
//        apiError.setMessage("Validation error");
//        apiError.addValidationErrors(ex.getConstraintViolations());
//        return buildResponseEntity(apiError);
//    }




    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return handleExceptionInternal(
                ex,
                buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex)),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return handleExceptionInternal(
                ex,
                buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex)),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


}
