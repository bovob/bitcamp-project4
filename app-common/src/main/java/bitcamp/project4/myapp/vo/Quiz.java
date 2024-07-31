package bitcamp.project4.myapp.vo;

import java.io.Serializable;
import java.util.Objects;

public class Quiz implements Serializable {

  private static final long serialVersionUID = 1L;

  private int no;
  private int number;
  private String answer;
  private String topic;
  private String hint;

  public Quiz() {
  }

  public Quiz(int no) {
    this.no = no;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Quiz quiz)) {
      return false;
    }
      return no == quiz.no && number == quiz.number && Objects.equals(answer, quiz.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(no, number, answer);
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }


}
