//package application;
//
//        import org.springframework.http.HttpStatus;
//        import org.springframework.http.ResponseEntity;
//        import java.util.*;
//
//public class ControllerExceptionHandler {
//
//    public ResponseEntity<Object> response(String message,HttpStatus status) {
//        if(status == HttpStatus.OK)
//            return new ResponseEntity<>(message,status);
//        Map<String, Object> body = new LinkedHashMap<>();
//        List<Object> messages = new ArrayList<>();
//        messages.add(message);
//        //body.put("timestamp", new Date());
//        //body.put("status", status.value());
//        body.put("message", messages);
//        return new ResponseEntity<Object>(body, status);
//    }
//}
