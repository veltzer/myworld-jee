.PHONY: all
all:
	@true

.PHONY: clean_hard
clean_hard:
	$(Q)git clean -qffxd
