package demo.ksug.board.module;

import demo.ksug.board.Board;
import demo.ksug.board.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Project: SpringBootHandsonlan
 * FileName: BoardService
 * Date: 2016-08-05
 * Time: 오후 6:04
 * Author: Hadeslee
 * Note:
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class BoardService {
    private BoardRepository boardRepository;
    private PostRepository postRepository;

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Board findBoard(String boardname) {
        Board board = boardRepository.findByName(boardname);
        if (Objects.isNull(board)) {
            throw new BoardNotFoundException(boardname);
        }
        return board;
    }

    public List<Post> findPosts(String boardname) {
        Board board = findBoard(boardname);
        return postRepository.findByBoard(board);
    }

    private Post getPost(long postId) {
        Post post = postRepository.findOne(postId);
        return post;
    }

    public Post writePost(String boardname, PostForm postForm) {
        Board board = findBoard(boardname);
        Post post = board.write(postForm.getAuthor(), postForm.getTitle(), postForm.getContent());
        return postRepository.save(post);
    }

    public Post editPost(long postId, String author, String title, String content) {
        Post post = getPost(postId);

        return post.update(author, title, content);
    }

    public Post erasePost(long postId) {
        Post post = getPost(postId);
        postRepository.delete(postId);
        return post;
    }
}
