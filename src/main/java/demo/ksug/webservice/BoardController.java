package demo.ksug.webservice;

import demo.ksug.board.Board;
import demo.ksug.board.Post;
import demo.ksug.board.module.BoardService;
import demo.ksug.board.module.PostForm;
import demo.ksug.board.module.ResourceNotFoundException;
import demo.ksug.webservice.support.MessageSourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Project: SpringBootHandsonlan
 * FileName: BoardController
 * Date: 2016-08-08
 * Time: 오전 9:45
 * Author: Hadeslee
 * Note:
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    private MessageSourceAccessor messageSource;

    @Autowired
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/{boardname}/info", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Board> info(@PathVariable String boardname) {
        return ResponseEntity.ok(boardService.findBoard(boardname));
    }

    @RequestMapping(value = "{boardname}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Board> free(@PathVariable String boardname) {
        System.out.println(boardname);
        return ResponseEntity.ok(boardService.findBoard(boardname));
    }

    @RequestMapping(value = "{boardname}/list", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<List<Post>> listPosts(@PathVariable String boardname) {
        return ResponseEntity.ok(boardService.findPosts(boardname));
    }

    @RequestMapping(value = "{boardname}", method = {RequestMethod.POST})
    public ResponseEntity<Post> createPost(@PathVariable String boardname, @Valid PostForm postForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.writePost(boardname, postForm));
    }

    @RequestMapping(value = "/{boardname}/{postId}", method = RequestMethod.PUT)
    public ResponseEntity<Post> updatePost(@PathVariable long postId, String author, String title, String content) {
        return ResponseEntity.ok(boardService.editPost(postId, author, title, content));
    }

    @RequestMapping(value = "{boardname}/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<Post> deletePost(@PathVariable long postId, String author, String title, String content) {
        return ResponseEntity.ok(boardService.erasePost(postId));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFoundException(ResourceNotFoundException exption, Locale locale) {
        System.out.println(exption.getError());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", exption.getStatus());
        body.put("error", exption.getError());
        body.put("message", messageSource.getMessage(exption.getCode(), exption.getArgs(), locale).orElse("No message available"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
