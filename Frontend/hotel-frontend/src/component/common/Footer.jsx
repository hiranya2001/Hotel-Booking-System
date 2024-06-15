const FooterComponent = () => {
  return (
    <footer>
      <span className="my-footer">
        My Hotel | All Right Reserved &copy; {new Date().getFullYear()}
      </span>
    </footer>
  );
};

export default FooterComponent;
