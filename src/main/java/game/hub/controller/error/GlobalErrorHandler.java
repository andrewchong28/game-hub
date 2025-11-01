//Package: this should be in game.hub.controller.error 
package game.hub.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/*
 * GlobalErrorHandler
 * ----------------------
 * This class is a GLOBAL exception handler for the GameHub application.
 * 
 * - Catches exceptions thrown from any controller.
 * - Returns a clean JSON error message instead of a full stack trace.
 * - Improves user experience by sending clear and consistent HTTP responses.
 */
@RestControllerAdvice // 🔹 Applies globally to all controllers in GameHub
@Slf4j                // 🔹 Lombok provides a "log" object for logging
public class GlobalErrorHandler {

    /*
     *Handles NoSuchElementException
     * --------------------------------
     * Thrown when an entity (like Developer, Game, or Genre) is not found
     * — for example, when a client provides a non-existent ID.
     * 
     * - Responds with HTTP 404 (Not Found)
     * - Logs a simple error message (without full stack trace)
     * - Returns a JSON response containing the error message
     */
    @ExceptionHandler(NoSuchElementException.class) // 🔹 Handles "not found" exceptions
    @ResponseStatus(HttpStatus.NOT_FOUND)           // 🔹 Sends a 404 response code
    public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {

        // Log the missing entity (for debugging)
        log.error("Entity not found: {}", ex.getMessage());

        // Return a minimal JSON message, e.g.:
        // { "message": "Developer with ID=5 was not found." }
        return Map.of("message", ex.getMessage());
    }

    /*
     * Handles any other unexpected Exception
     * ----------------------------------------
     * Catch-all handler for unanticipated issues.
     * 
     * - Responds with HTTP 500 (Internal Server Error)
     * - Logs the exception details (stack trace included)
     * - Returns a user-friendly JSON error message
     */
    @ExceptionHandler(Exception.class) // 🔹 Catches all remaining exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 🔹 Sends a 500 response
    public Map<String, String> handleGenericException(Exception ex) {

        //Log the full exception details for debugging
        log.error("Unexpected error occurred", ex);

        //Return a safe, generic message (don’t leak internal details)
        return Map.of("message", "An unexpected error occurred. Please try again later.");
    }
}