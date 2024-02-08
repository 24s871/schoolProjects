library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity numarator is
	port(enable:in std_logic;
	clock,reset:in std_logic;
	counter:out std_logic_vector (3 downto 0);
	tc:out std_logic);
end entity;

architecture cmp of numarator is
signal tmp:std_logic_vector (3 downto 0);
begin
	process(clock,reset)
	begin 
		if(enable<='1') then
		if (reset='1') then
			tmp<="0000";
		elsif (clock='1' and clock'event) then
			if(tmp="1111") then
				tmp<=tmp+'1';
			else tc<='1';
			end if;
			end if;
			else tc<='0';
			end if;
		end process;
		counter<=tmp;
		end architecture;