package vn.edu.ueh.thanhdnh.firebase_example;

public class Article {

  private String documentId;
  private String title;
  private String content;

  public Article() {
  }

  public Article(
          String documentId,
          String title,
          String content) {

    this.documentId = documentId;
    this.title = title;
    this.content = content;
  }

  public Article(
          String title,
          String content) {

    this.title = title;
    this.content = content;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}