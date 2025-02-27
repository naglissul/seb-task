package sebtask.exrates.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sebtask.exrates.dto.MessageDto

@RestController
@RequestMapping("/main")
class MainController {

    @GetMapping("/hello", produces = ["application/json"])
    fun hello(): ResponseEntity<MessageDto> {
        return ResponseEntity.ok(MessageDto("Hello world!"))
    }

}