import com.huade.dao.BookReviewDao;
import com.huade.dao.impl.BookReviewDaoImpl;
import com.huade.entity.BookReview;
import org.junit.Test;

import java.util.List;

public class BookReviewDaoTest {

    BookReviewDao bookReviewDao = new BookReviewDaoImpl();

    @Test
    public void testFindAll() {
        List<BookReview> list = bookReviewDao.findAll();
        for (BookReview r : list) {
            System.out.println(r);
        }
    }

    @Test
    public void testFindById() {
        BookReview review = bookReviewDao.findById(1);
        if (review != null) {
            System.out.println("查询成功：" + review);
        } else {
            System.out.println("查询失败：该评价不存在");
        }
    }

    @Test
    public void testInsert() {
        BookReview review = new BookReview();
        review.setBookTitle("测试图书");
        review.setReviewerName("测试读者");
        review.setRating(4);
        review.setReviewContent("这是一条测试评价内容。");
        review.setReviewTime("2026-07-01 12:00:00");
        bookReviewDao.insert(review);
        System.out.println("插入成功");
    }

    @Test
    public void testUpdate() {
        BookReview review = new BookReview();
        review.setId(1);
        review.setBookTitle("修改后的书名");
        review.setReviewerName("修改后的评价人");
        review.setRating(3);
        review.setReviewContent("修改后的评价内容");
        review.setReviewTime("2026-07-01 14:00:00");
        int num = bookReviewDao.update(review);
        if (num > 0) {
            System.out.println("修改成功，影响行数：" + num);
        } else {
            System.out.println("修改失败，该评价可能不存在");
        }
    }

    @Test
    public void testDelete() {
        int id = 27;
        int num = bookReviewDao.delete(id);
        if (num > 0) {
            System.out.println("删除成功，影响行数：" + num);
        } else {
            System.out.println("删除失败，该评价可能不存在");
        }
    }

    @Test
    public void testSearch() {
        List<BookReview> list = bookReviewDao.search("Java", 1, 10);
        System.out.println("搜索结果数：" + list.size());
        for (BookReview r : list) {
            System.out.println(r);
        }
    }

    @Test
    public void testCount() {
        int total = bookReviewDao.count("Java");
        System.out.println("匹配记录数：" + total);
    }
}
