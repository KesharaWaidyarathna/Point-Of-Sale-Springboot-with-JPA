package lk.ijse.dep.pos.advice;


import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@CrossOrigin
@RestControllerAdvice
public class AppWideExceptionHandler {

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public String globalExceptionHandler(Exception e){
//        e.printStackTrace();
//        return "Something went Wong plz contact the developer team" ;
//
//    }

    @ExceptionHandler({NotFoundException.class, javax.persistence.EntityNotFoundException.class})
    public ResponseEntity handelNotFoundException(NotFoundException e){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
